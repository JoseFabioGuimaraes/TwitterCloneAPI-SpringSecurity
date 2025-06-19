package br.com.jfabiodev.TwitterClone.repository;

import br.com.jfabiodev.TwitterClone.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
