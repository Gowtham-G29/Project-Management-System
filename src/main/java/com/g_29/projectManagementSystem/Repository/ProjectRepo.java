package com.g_29.projectManagementSystem.Repository;

import com.g_29.projectManagementSystem.model.Project;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long> {

//    List<Project>findByOwner(User user);
//
//    @Query(value = "SELECT p FROM Project p JOIN p.team t WHERE t=:user",nativeQuery = true)
//    List<Project>findProjectByTeam(@Param("user") User user);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    List<Project>findByTeamContainingOrOwner(User user,User owner);

}
