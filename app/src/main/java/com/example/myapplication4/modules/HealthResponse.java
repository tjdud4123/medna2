package com.example.myapplication4.modules;

/*
    "certFile":"BASE64로 Encoding된 인증서 der파일 문자열",
    "keyFile":"BASE64로 Encoding된 인증서 key파일 문자열",
    "certPassword":"RSA암호화된 비밀번호",
    "certType": "pfx",
    "identity": "8************",
    "inquiryType": "3",
    "searchStartYear": "2019",
    "searchEndYear": "2019",
    "type": "1"
    "userId": "a***@gmail.com",
    "userPassword": "RSA암호화된 비밀번호"
 */

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class HealthResponse {
    private String certFile, certPassword, identityNo, inquiryType, searchEndYear, searchStartYear, type, userId, userPassword;

}
