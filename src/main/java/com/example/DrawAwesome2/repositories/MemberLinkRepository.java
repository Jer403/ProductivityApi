package com.example.DrawAwesome2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.Groups;
import com.example.DrawAwesome2.models.MemberLinks;

@org.springframework.stereotype.Repository
public interface MemberLinkRepository extends JpaRepository<MemberLinks, String> {

	List<MemberLinks> findByUserid(Long id);
	List<MemberLinks> findByGroupid(Long id);
	Optional<MemberLinks> findByGroupidAndUserid(Long gid,Long uid);
}
