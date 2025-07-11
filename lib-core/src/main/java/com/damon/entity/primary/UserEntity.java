package com.damon.entity.primary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * 用户实体类
 *
 * @author damon du/minghongdud
 */
@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity implements Serializable {


    @Serial
    private static final long serialVersionUID = 2025L;

    @Id
    @Column(name = "USER_ID", unique = true, columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "USER_NAME", unique = true, columnDefinition = "VARCHAR", length = 64)
    private String userName;

    @Column(name = "NICK_NAME", unique = true, columnDefinition = "VARCHAR", length = 64)
    private String nickName;

    @Column(name = "USER_PASSWORD", columnDefinition = "VARCHAR", length = 64)
    private String userPassword;

    @Column(name = "USER_SEX", columnDefinition = "CHAR", length = 1)
    private String userSex;

    @Column(name = "USER_TYPE", columnDefinition = "CHAR", length = 1)
    private String userType;

    @Column(name = "USER_EMAIL", columnDefinition = "VARCHAR", length = 20)
    private String userEmail;

    @Column(name = "USER_PHONE_NUMBER", columnDefinition = "VARCHAR", length = 11)
    private String userPhoneNumber;

    @Column(name = "USER_PERSONALIZED_SIGNATURE", columnDefinition = "VARCHAR", length = 300)
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    private String userPersonalizedSignature;

    @Column(name = "CREATE_BY")
    @CreatedBy
    private Long createBy;

    @Column(name = "UPDATE_BY")
    @LastModifiedBy
    private Long updateBy;

    @Column(name = "CREATE_TIME")
    @CreatedDate
    private LocalTime createTime;

    @Column(name = "UPDATE_TIME")
    @LastModifiedDate
    private LocalTime updateTime;

    @Column(name = "VERSION")
    private @Version Long version;

    @Column(name = "DEL_FLAG")
    private Integer delFlag;

}
