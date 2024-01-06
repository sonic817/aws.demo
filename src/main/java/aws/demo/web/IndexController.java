package aws.demo.web;

import aws.demo.config.auth.dto.SessionUser;
import aws.demo.domain.Posts.Posts;
import aws.demo.service.PostsService;
import aws.demo.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("")
    public String index(Model model) {
        JSONObject jsonResponse = postsService.findAllDesc();
        List<PostsListResponseDto> PostsList = (List<PostsListResponseDto>) jsonResponse.get("data");
        model.addAttribute("posts", PostsList);

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("username", user.getName());
        }

        return "index";
    }

    @GetMapping("posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(
            Model model,
            @PathVariable Long id
    ) {
        JSONObject jsonResponse = postsService.findById(id);
        Posts posts = (Posts) jsonResponse.get("data");
        model.addAttribute("post", posts);

        return "posts-update";
    }
}