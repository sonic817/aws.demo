package aws.demo.service;

import aws.demo.domain.Posts.PostsRepository;
import aws.demo.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public JSONObject save(PostsSaveRequestDto requestDto) {
        JSONObject jsonResponse = new JSONObject();

        try {
            Long id = postsRepository.save(requestDto.toEntity()).getId();

            jsonResponse.put("message", "success");
            jsonResponse.put("data", id);

        } catch (Exception e) {
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("data", "");
        }

        return jsonResponse;
    }
}