package com.ability_plus.forum.controller;


import com.ability_plus.forum.entity.Reply;
import com.ability_plus.forum.service.IReplyService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/forum/reply")
public class ReplyController {

    @Autowired
    IReplyService replyService;

    @PostMapping("/new_reply")
    @ApiOperation("new a reply")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "data", required = true),
            @ApiImplicitParam(name = "projectId", value = "projectId", required = true),
            @ApiImplicitParam(name="isPin",value="isPin")
    })
    public RestResponse newReply(@RequestParam(value = "data") String data,
                                @RequestParam(value = "postId") Integer postId,
                                HttpServletRequest http){
        replyService.newReply(data,postId,http);
        return RestResponse.success();
    }
}
