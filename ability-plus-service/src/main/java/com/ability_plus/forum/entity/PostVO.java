package com.ability_plus.forum.entity;

import lombok.Data;

@Data
public class PostVO {
    private Integer postId;
    private String data;
    private String authName;
    private Integer authId;
}