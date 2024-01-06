package aws.demo.web;

import aws.demo.service.PostsService;
import aws.demo.web.dto.PostsSaveRequestDto;
import aws.demo.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;

    @PostMapping("api/v1/posts")
    public JSONObject save(
            @RequestBody PostsSaveRequestDto requestDto
    ) {
        return postsService.save(requestDto);
    }

    @GetMapping("api/v1/posts")
    public JSONObject findById(
            @RequestParam Long id
    ) {
        return postsService.findById(id);
    }

    @PutMapping("api/v1/posts/{id}")
    public JSONObject update(
            @PathVariable Long id,
            @RequestBody PostsUpdateRequestDto requestDto
    ) {
        return postsService.update(id, requestDto);
    }


    @DeleteMapping("api/v1/posts/{id}")
    public JSONObject delete(
            @PathVariable Long id
    ) {
        JSONObject jsonResponse = postsService.delete(id);
        return jsonResponse;
    }
}