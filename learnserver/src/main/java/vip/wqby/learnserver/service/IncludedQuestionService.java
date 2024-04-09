package vip.wqby.learnserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.core.TimeValue;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import vip.wqby.learnserver.pojo.IncludedQuestion;

import java.io.IOException;
import java.util.*;

@Service
public class IncludedQuestionService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;


    public void insert (String type, String rawQuestion, String question, String options, String courseName, String platform) throws IOException {
        //当前时间-8小时 时区差
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -8);
        Date addTime = calendar.getTime();
        IncludedQuestion q = new IncludedQuestion(addTime,type,rawQuestion, question, options, courseName, platform);
        IndexRequest request = new IndexRequest("included");
        String id = DigestUtils.md5DigestAsHex((type + question + options).getBytes());
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(60));
        //请求放入json
        IndexRequest source = request.source(JSON.toJSONString(q), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public String getRandomIncluded(int size) throws IOException {
        ArrayList<Map<String, Object>> response = new ArrayList<>();
        SearchRequest request = new SearchRequest("included");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        Script script = new Script("Math.random()");
        ScriptSortBuilder sortBuilder = new ScriptSortBuilder(script, ScriptSortBuilder.ScriptSortType.NUMBER);
        sourceBuilder.sort(sortBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(size);
        request.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        for (SearchHit documentFields : searchResponse.getHits()) {
            Map<String,Object>temporary = new HashMap<>();
            temporary = documentFields.getSourceAsMap();
            temporary.put("id",documentFields.getId());

            response.add(temporary);
        }
        return JSONObject.toJSONString(response);
    }

    public void deleteById(String id) throws IOException {
        DeleteRequest request = new DeleteRequest("included",id);
        DeleteResponse dResponse = restHighLevelClient.delete(request,RequestOptions.DEFAULT);
    }
}
