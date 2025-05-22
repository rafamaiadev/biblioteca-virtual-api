package br.com.raphael.biblioteca_virtual_api.repository.specification;

import br.com.raphael.biblioteca_virtual_api.domain.filter.LivroFilter;
import br.com.raphael.biblioteca_virtual_api.domain.model.Livro;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LivroSpecification {

    public static Specification<Livro> filterBy(LivroFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> searchPredicates = new ArrayList<>();
            
                
            if (filter.getSearch() != null && !filter.getSearch().trim().isEmpty()) {
                String searchTerm = "%" + filter.getSearch().toLowerCase() + "%";
                searchPredicates.add(cb.like(cb.lower(root.get("titulo")), searchTerm));
                searchPredicates.add(cb.like(cb.lower(root.get("autor")), searchTerm));
                searchPredicates.add(cb.like(cb.lower(root.get("categoria")), searchTerm));
                return cb.or(searchPredicates.toArray(new Predicate[0]));
            }

            if (filter.getTitulo() != null && !filter.getTitulo().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("titulo")), 
                    "%" + filter.getTitulo().toLowerCase() + "%"));
            }

            if (filter.getAutor() != null && !filter.getAutor().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("autor")), 
                    "%" + filter.getAutor().toLowerCase() + "%"));
            }

            if (filter.getCategoria() != null && !filter.getCategoria().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("categoria")), 
                    "%" + filter.getCategoria().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
} 