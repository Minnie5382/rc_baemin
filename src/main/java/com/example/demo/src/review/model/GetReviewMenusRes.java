package com.example.demo.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetReviewMenusRes {
    private int orderId;
    private String menu;
}
