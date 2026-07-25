package br.com.bjjacademy.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faixa")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Faixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = false, unique = true)
    private Integer ordem;

    @Column(name = "idade_minima")
    private Integer idadeMinima;

    @Column(name = "quantidade_maxima_graus", nullable = false)
    private Integer quantidadeMaximaGraus;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}
