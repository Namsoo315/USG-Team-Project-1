package com.example.gateway.security.filter;

import com.example.gateway.domain.member.Member;
import com.example.gateway.domain.member.Role;
import com.example.gateway.repository.MemberRepository;
import com.example.gateway.security.api.ApiList;
import com.example.gateway.token.TokenParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {
    private final TokenParser parser;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String requestURI = request.getURI().getPath();
            boolean isBlackList = checkBlackList(requestURI);
            boolean isOwnerList = checkOwnerList(requestURI);

            try {
                if (!isBlackList) {
                    chain.filter(exchange);
                } else if (isBlackList && isOwnerList) {
                    String authorizationHeader = request.getHeaders().getFirst("Authorization");
                    String token;
                    String email;

                    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                        token = authorizationHeader.substring(7);
                        // 만료 체크
                        if (parser.isExpiration(token)) {
                            throw new IllegalArgumentException("AccessToken Expired");
                        }

                        // claim 을 받아와 정보 추출
                        email = (String) parser.getToken(token).get("email");

                        // DB 에 정보가 있는지 확인
                        Member findMember = memberRepository.findByEmail(email)
                                .orElseThrow(() -> new IllegalArgumentException("Member Not Exist"));

                        if (!findMember.getRole().equals(Role.Owner)) {
                            throw new IllegalArgumentException("Role Not Match");
                        }

                        return chain.filter(exchange);
                    } else {
                        throw new IllegalArgumentException("Token Not Exist");
                    }
                } else if (isBlackList) {
                    String authorizationHeader = request.getHeaders().getFirst("Authorization");
                    String token;
                    String email;

                    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                        token = authorizationHeader.substring(7);
                        // 만료 체크
                        if (parser.isExpiration(token)) {
                            throw new IllegalArgumentException("AccessToken Expired");
                        }

                        // claim 을 받아와 정보 추출
                        email = (String) parser.getToken(token).get("email");

                        // DB 에 정보가 있는지 확인
                        Member findMember = memberRepository.findByEmail(email)
                                .orElseThrow(() -> new IllegalArgumentException("Member Not Exist"));

                        return chain.filter(exchange);
                    } else {
                        throw new IllegalArgumentException("Token Not Exist");
                    }
                }
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }

    private boolean checkOwnerList(String requestURI) {
        boolean isOwnerList = false;

        for (String ownerList : ApiList.getOwnerList()) {
            if (isPatternMatch(requestURI, ownerList)) {
                isOwnerList = true;
                break;
            }
        }

        return isOwnerList;
    }

    private boolean checkBlackList(String requestURI) {
        boolean isBlackListed = false;

        for (String blackList : ApiList.getBlackList()) {
            if (isPatternMatch(requestURI, blackList)) {
                isBlackListed = true;
                break;
            }
        }

        return isBlackListed;
    }

    private boolean isPatternMatch(String url, String pattern) {
        // 패턴이 일치하면 true 반환, **를 정규 표현식으로 처리
        return url.equals(pattern) || url.matches(pattern.replace("**", ".*"))
                || (pattern.endsWith("/**") && url.startsWith(pattern.substring(0, pattern.length() - 3)));
    }
}
