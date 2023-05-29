package com.ezen.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezen.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
