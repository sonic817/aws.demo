package aws.demo.web;

import aws.demo.service.PostsService;
import aws.demo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("")
    public String index(Model model) {
        JSONObject jsonResponse = postsService.findAllDesc();
        List<PostsResponseDto> PostsList = (List<PostsResponseDto>) jsonResponse.get("data");
        model.addAttribute("posts", PostsList);
        return "index";
    }

    @GetMapping("posts/save")
    public String postsSave() {
        return "posts-save";
    }
}