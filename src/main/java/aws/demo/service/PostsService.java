package aws.demo.service;

import aws.demo.domain.Posts.Posts;
import aws.demo.domain.Posts.PostsRepository;
import aws.demo.web.dto.PostsListResponseDto;
import aws.demo.web.dto.PostsSaveRequestDto;
import aws.demo.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public JSONObject findById(Long id) {
        JSONObject jsonResponse = new JSONObject();

        try {
            Posts entity = postsRepository.findById(id).orElse(null);

            if (entity == null) {
                jsonResponse.put("message", "success");
                jsonResponse.put("data", new HashMap<>());
            } else {
                jsonResponse.put("message", "success");
                jsonResponse.put("data", entity);
            }
        } catch (Exception e) {
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("data", "");
        }

        return jsonResponse;
    }

    @Transactional
    public JSONObject update(Long id, PostsUpdateRequestDto requestDto) {
        JSONObject jsonResponse = new JSONObject();

        try {
            Posts posts = postsRepository.findById(id).orElse(null);

            if (posts != null) {
                posts.update(requestDto.getTitle(), requestDto.getContent());

                jsonResponse.put("message", "success");
                jsonResponse.put("data", posts);
            } else {
                jsonResponse.put("message", "success");
                jsonResponse.put("data", new HashMap<>());
            }
        } catch (Exception e) {
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("data", "");
        }

        return jsonResponse;
    }

    @Transactional(readOnly = true)
    public JSONObject findAllDesc() {
        JSONObject jsonResponse = new JSONObject();

        try {
            List<PostsListResponseDto> postsList = postsRepository.findAllDesc().
                    stream().
                    map(PostsListResponseDto::new).
                    collect(Collectors.toList());


            if (!postsList.isEmpty()) {
                jsonResponse.put("message", "success");
                jsonResponse.put("data", postsList);
            } else {
                jsonResponse.put("message", "success");
                jsonResponse.put("data", new ArrayList<>());
            }
        } catch (Exception e) {
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("data", "");
        }

        return jsonResponse;
    }

    @Transactional()
    public JSONObject delete(Long id) {
        JSONObject jsonResponse = new JSONObject();

        try {
            Posts posts = postsRepository.findById(id).orElse(null);

            if (posts != null) {
                postsRepository.delete(posts);

                jsonResponse.put("message", "success");
                jsonResponse.put("data", posts);
            } else {
                jsonResponse.put("message", "success");
                jsonResponse.put("data", new HashMap<>());
            }
        } catch (Exception e) {
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("data", "");
        }

        return jsonResponse;
    }
}