//$Id$
package com.patientHub.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientHub.main.model.Iamuser;

public interface IamuserRepo extends JpaRepository<Iamuser, String> {

}
