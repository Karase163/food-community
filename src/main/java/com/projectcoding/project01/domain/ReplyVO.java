package com.projectcoding.project01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReplyVO {

    private int replyId;                    
    private int commentsId;                  
    private String memberId;                   
    private String replyContent;            
    private Date replyDateCreated;          

  
}
