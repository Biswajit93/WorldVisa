package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.UniqueID;

@Repository("uniqueIDRepository")
public interface UniqueIDRepository  extends JpaRepository<UniqueID, Integer>{

}
