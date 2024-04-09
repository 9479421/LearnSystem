package vip.wqby.learnserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import okhttp3.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import vip.wqby.learnserver.pojo.Question;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;


@Service
public class QuestionService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public String insert(String type, String rawQuestion, String question, String options, String answer, String courseName, String platform) throws IOException {
        //当前时间-8小时 时区差
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -8);
        Date addTime = calendar.getTime();

        Question q = new Question(type, rawQuestion, question, options, answer, courseName, platform, addTime);
        IndexRequest request = new IndexRequest("question");
        String id = DigestUtils.md5DigestAsHex((type + question + options).getBytes());
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(60));
        request.source(JSON.toJSONString(q), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }

    public String getOnceQuestions(String qq) throws IOException {
        SearchRequest searchRequest = new SearchRequest("question");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("courseName", qq);
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("platform", "selfhelp");
        SortBuilder sortBuilder = SortBuilders.fieldSort("addTime")
                .order(SortOrder.DESC);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(termQueryBuilder)
                .must(termQueryBuilder1);
        sourceBuilder.query(boolQueryBuilder).sort(sortBuilder).from(0).size(10);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        String ret = "";
        for (int i = 0; i < searchHits.length; i++) {
            SearchHit hit = searchHits[i];
            if (i == searchHits.length - 1) {
                ret += hit.getSourceAsMap().get("question") + "  " + hit.getSourceAsMap().get("answer") + "  " + "[" + hit.getId() + "]";
            } else {
                ret += hit.getSourceAsMap().get("question") + "  " + hit.getSourceAsMap().get("answer") + "  " + "[" + hit.getId() + "]\n";
            }
        }
        return ret;


    }

    public String search(String q, String ops) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = null;
        if (ops.equals("")) {
            body = RequestBody.create(mediaType, "{\n  \"query\": {\n    \"match\": {\n      \"question\": \"" +
                    transfer(q) + "\"\n    }\n  }\n}");
        } else {
            body = RequestBody.create(mediaType, "{\n" +
                    "  \"query\": {\n" +
                    "    \"bool\":{\n" +
                    "      \"must\":[\n" +
                    "        {\n" +
                    "         \"match\": {\n" +
                    "            \"question\": \"" + transfer(q) + "\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        {  \"match\": {\n" +
                    "            \"options\": \"" + transfer(ops) + "\"\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "   \n" +
                    "  }\n" +
                    "}");
        }
        Request request = new Request.Builder()
                .url("http://localhost:9200/question/_doc/_search")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        JSONObject jsonObject_hits = JSONObject.parseObject(jsonObject.getString("hits"));
        JSONArray jsonArray_hits = jsonObject_hits.getJSONArray("hits");
        if (jsonArray_hits.size() > 0) {
            JSONObject firstText = jsonArray_hits.getJSONObject(0);
            JSONObject jsonObject_source = firstText.getJSONObject("_source");
            String type = jsonObject_source.getString("type");
            String question = jsonObject_source.getString("question");
            String rawQuestion = jsonObject_source.getString("rawQuestion");
            String options = jsonObject_source.getString("options");
            String answer = jsonObject_source.getString("answer");
            JSONObject data = new JSONObject();
            data.put("type", type);
            data.put("question", question);
            data.put("rawQuestion", rawQuestion);
            data.put("options", options);
            data.put("answer", answer);
            return data.toJSONString();
        } else {
            return "{}";
        }
    }


    public String singlesearch(String q) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"query\": {\n    \"match\": {\n      \"question\": \"" +
                transfer(q) + "\"\n    }\n  }\n}");
        Request request = new Request.Builder()
                .url("http://localhost:9200/question/_doc/_search")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        JSONObject jsonObject_hits = JSONObject.parseObject(jsonObject.getString("hits"));
        JSONArray jsonArray_hits = jsonObject_hits.getJSONArray("hits");
        if (jsonArray_hits.size() > 0) {
            JSONObject firstText = jsonArray_hits.getJSONObject(0);
            JSONObject jsonObject_source = firstText.getJSONObject("_source");
            String type = jsonObject_source.getString("type");
            String question = jsonObject_source.getString("question");
            String rawQuestion = jsonObject_source.getString("rawQuestion");
            String options = jsonObject_source.getString("options");
            String answer = jsonObject_source.getString("answer");
            JSONObject data = new JSONObject();
            data.put("type", type);
            data.put("question", question);
            data.put("rawQuestion", rawQuestion);
            data.put("options", options);
            data.put("answer", answer);
            return data.toJSONString();
        } else {
            return "{}";
        }
    }

    public static String transfer(String rawString) {
        return rawString.replaceAll("\\\\", "").replaceAll("\"", "");
    }

    public void addAnswer(String id, String answer) throws IOException {
        SearchRequest request = new SearchRequest("included");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("_id", id);
        sourceBuilder.query(termQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits.getTotalHits().value >= 1) {
            Map<String, Object> hit = new HashMap<>();
            hit = searchHits.getAt(0).getSourceAsMap();
            String type = (String) hit.get("type");
            String rawQuestion = (String) hit.get("rawQuestion");
            String question = (String) hit.get("question");
            String options = (String) hit.get("options");
            String courseName = (String) hit.get("courseName");
            String platform = (String) hit.get("platform");
            //插入到question
            insert(type, rawQuestion, question, options, answer, courseName, platform);
        }

    }

    public String deleteById(String id, String qq) throws IOException {
        System.out.println(id);
        System.out.println(qq);
        SearchRequest request = new SearchRequest("question");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("_id", id);
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("courseName", qq);
        TermQueryBuilder termQueryBuilder2 = QueryBuilders.termQuery("platform", "selfhelp");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(termQueryBuilder)
                .must(termQueryBuilder1)
                .must(termQueryBuilder2);
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits.getTotalHits().value > 0) {
            DeleteRequest deleteRequest = new DeleteRequest("question", id);
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);//DeleteResponse dResponse =
            return "true";
        } else {
            return "false";
        }

    }
}
