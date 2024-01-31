package com.example.loc.domain.Image;

import com.example.loc.domain.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;
    private String uploadFilename; // 사용자가 업로드한 파일명
    private String storeFilename; // 서버에 저장한 파일명
    private String s3Url; // 파일 저장 경로

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review; // 이미지, 리뷰 -> N : 1

    @Builder
    public Image(String uploadFilename, String storeFilename, String s3Url, Review review) {
        this.uploadFilename = uploadFilename;
        this.storeFilename = storeFilename;
        this.s3Url = s3Url;
        this.review = review;
    }

}
