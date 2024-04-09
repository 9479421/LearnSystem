package vip.wqby.learnserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.wqby.learnserver.service.QuestionService;
import vip.wqby.learnserver.service.IncludedQuestionService;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/client/question")
public class SearchController {
    @Autowired
    QuestionService questionService;
    @Autowired
    IncludedQuestionService includedQuestionService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("type")  String  type ,
                         @RequestParam("rawQuestion") String rawQuestion,
                         @RequestParam("question") String question,
                         @RequestParam("options") String options,
                         @RequestParam("answer") String answer,
                         @RequestParam("courseName") String courseName,
                         @RequestParam("platform") String platform) throws IOException {
        return questionService.insert(type, rawQuestion,question ,options,answer, courseName, platform);
    }

    @RequestMapping("/search")
    public String search(@RequestParam("question") String question,
                         @RequestParam("options") String options
    ) throws IOException {
        return questionService.search(question, options);
    }


    @RequestMapping("/singlesearch")
    public String search(@RequestParam("question") String question) throws IOException {
        return questionService.singlesearch(question);
    }

    @RequestMapping("/insertIncludedQuestion")
    public String insertIncludedQuestion(@RequestParam("type") String type,
                                         @RequestParam("rawQuestion") String rawQuestion,
                                         @RequestParam("question") String question,
                                         @RequestParam("options") String options,
                                         @RequestParam("courseName") String courseName,
                                         @RequestParam("platform") String platform) throws IOException {

        includedQuestionService.insert(type, rawQuestion, question, options, courseName, platform);
        return "200";
    }

    @RequestMapping("/getRandomIncluded") //localhost:9876/getRandomIncluded?size=1
    public String getRandomIncluded(@RequestParam("size")int size) throws IOException {
        return includedQuestionService.getRandomIncluded(size);
    }

    @RequestMapping("deleteAndInsert")
    public String deleteAndInsert(@RequestParam("id")String id,@RequestParam("answer")String answer) throws IOException {
        questionService.addAnswer(id,answer);
        includedQuestionService.deleteById(id);
        return "200";
    }

    @RequestMapping("deleteByIdAndInsertAnswer")
    public String deleteByIdAndInsertAnswer(@RequestParam("id")String id,@RequestParam("answer")String answer) throws IOException {
        questionService.addAnswer(id,answer);
        includedQuestionService.deleteById(id);
        return "200";
    }


    @RequestMapping("getOnceQuestions")
    public String getOnceQuestions(@RequestBody String qq) throws IOException {
        return questionService.getOnceQuestions(qq);
    }

    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id")String id,@RequestParam("qq")String qq) throws IOException {
        return questionService.deleteById(id,qq);
    }
//
//    @RequestMapping("rewriteAnswer")
//    public String rewriteAnswer(@RequestParam("id")String id,@RequestParam("answer")String answer){
//        questionService.rewriteAnswer(id,answer);
//        return "200";
//    }

}
