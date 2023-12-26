package aws.demo.web;

import aws.demo.service.PostsService;
import aws.demo.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;

    @GetMapping("")
    public JSONObject home() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "success");
        jsonResponse.put("data", "");
        return jsonResponse;
    }

    @PostMapping("api/v1/posts")
    public JSONObject save(
            @RequestBody PostsSaveRequestDto requestDto
    ) {
        JSONObject jsonResponse = postsService.save(requestDto);
        return jsonResponse;
    }
}