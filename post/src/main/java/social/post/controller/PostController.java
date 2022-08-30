package in.careerpower.social.post.controller;

import in.careerpower.social.post.model.CreatePostRequestModel;
import in.careerpower.social.post.model.CreatePostResponseModel;
import in.careerpower.social.post.model.PostDetailsResponseModel;
import in.careerpower.social.post.model.dto.PostDto;
import in.careerpower.social.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class PostController {

    @Autowired
    private Environment env;

    @Autowired
    private PostService postService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "/post/{postId}")
    @ResponseBody
    public PostDetailsResponseModel getPost(@PathVariable String postId ){
        PostDto requestedPost = postService.getPostDetailsByPostId(postId);
        return modelMapper.map(requestedPost,PostDetailsResponseModel.class);

    }

    @RequestMapping(value = "/status/check", method = RequestMethod.GET)
    public String status(){
        return "working on port " + env.getProperty("local.server.port");
    }

    @PostMapping(value = "/post")
    public CreatePostResponseModel createPost(@RequestBody CreatePostRequestModel postDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PostDto postDto = modelMapper.map(postDetails, PostDto.class);
        PostDto createdPost = postService.createPost(postDto);
        return modelMapper.map(createdPost, CreatePostResponseModel.class);
    }

}


