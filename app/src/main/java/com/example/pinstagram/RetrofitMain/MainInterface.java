package com.example.pinstagram.RetrofitMain;

import com.example.pinstagram.Ads.UserAdUrl;
import com.example.pinstagram.Comments.CommentDto;
import com.example.pinstagram.Feed.Story;
import com.example.pinstagram.Login.LoginDto;
import com.example.pinstagram.Login.LoginResDto;
import com.example.pinstagram.Login.LogoutDto;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.Post.ReactionDto;
import com.example.pinstagram.Register.RegisterDto;
import com.example.pinstagram.Register.StatusDto;
import com.example.pinstagram.SearchList.AddSearchDto;
import com.example.pinstagram.UserProfile.ConnectionDto;
import com.example.pinstagram.UserProfile.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MainInterface {
    // feed
    @GET("/feed/posts/{id}")
    Call<List<PostDto>> getFeedByUserId(@Path("id") String id);
    // Followers
    @GET("/connection/{id}/{type}")
    Call<List<String>> findFollowerByUserId(@Path("id") String userId,@Path("type") String type);

    @GET("/connection/{id}")
    Call<List<String>> findFollowingByUserId(@Path("id") String userId);

    @DELETE("/connection/delete/{uId}/{tId}")
    Call<Void> deleteConnection(@Path("uId") String userId,@Path("tId") String targetId);

    @POST("/connection/add")
    Call<Void> addConnection(@Body ConnectionDto connectionDto);

    @GET("/connection/getNoOfConnection/{id}")
    Call<List<Long>> getNoOfConnections(@Path("id") String userId);

    @GET("/connection/check/{userId}/{targetId}")
    Call<Boolean> checkConnection(@Path("userId") String userId,@Path("targetId") String targetId);

    //    Post for My Profile
    @GET("/post/getPostsByUserId/{id}")
    Call<List<PostDto>> getPostByUserId(@Path("id") String userId);

    // comments
    @GET("/comment/post/{id}")
    Call<List<CommentDto>> getCommentsForPost(@Path("id") Long postId);

    @GET("/comment/post/{id}/{parentId}")
    Call<List<CommentDto>> getCommentsByParentId(@Path("id")Long postId,@Path("parentId")Long parentId );

    @PUT("/comment/add")
    Call<Void> addComment(@Body CommentDto commentDto);

    //reaction
    @POST("/reaction")
    Call<Void> addReaction(@Body ReactionDto reactionDto);

//     stories
    @GET("/feed/stories/{id}")
    Call<List<Story>> getStoryByUserId(@Path("id") String userId);

    //post
    @POST("/post")
    Call<Void> addPost(@Body PostDto postDto);

// StoryPost
    @POST("/story")
    Call<Void> addStory(@Body Story story);
    //search
    @GET("/user/search/{query}")
    Call<List<UserDto>> search(@Path("query") String query);

//    Register
    @POST("/authentication/authenticate/register")
    Call<StatusDto> register(@Body RegisterDto registerDto);


//    Login
    @POST("/authentication/authenticate/login")
    Call<LoginResDto> login(@Body LoginDto loginDto);

    @POST("/authentication/authenticate/logout")
    Call<StatusDto> logout(@Body LogoutDto logoutDto);
// AddTOSearch
    @POST("/user/add")
    Call<Void> addToSearch (@Body AddSearchDto addSearchDto);


//    //    Ads
    @GET("/recommendation/recommend/{id}")
    Call<UserAdUrl> getAds (@Path("id") String userId);



}
