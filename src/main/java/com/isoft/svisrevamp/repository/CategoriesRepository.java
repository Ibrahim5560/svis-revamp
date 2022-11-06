package com.isoft.svisrevamp.repository;

import com.isoft.svisrevamp.domain.Categories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long>, JpaSpecificationExecutor<Categories> {}
