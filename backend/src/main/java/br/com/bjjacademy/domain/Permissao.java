package br.com.bjjacademy.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 300)
    private String descricao;
}
