package br.com.raphael.biblioteca_virtual_api.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.com.raphael.biblioteca_virtual_api.domain.filter.LivroFilter;
import br.com.raphael.biblioteca_virtual_api.domain.model.Livro;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class LivroSpecification {

    public static Specification<Livro> filterBy(LivroFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.getTitulo())) {
                predicates.add(cb.like(
                    cb.lower(root.get("titulo")),
                    "%" + filter.getTitulo().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(filter.getAutor())) {
                predicates.add(cb.like(
                    cb.lower(root.get("autor")),
                    "%" + filter.getAutor().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(filter.getIsbn())) {
                predicates.add(cb.like(
                    cb.lower(root.get("isbn")),
                    "%" + filter.getIsbn().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(filter.getCategoria())) {
                predicates.add(cb.like(
                    cb.lower(root.get("categoria")),
                    "%" + filter.getCategoria().toLowerCase() + "%"
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
} 