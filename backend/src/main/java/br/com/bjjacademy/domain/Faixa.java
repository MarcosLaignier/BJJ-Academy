package br.com.bjjacademy.domain;

import br.com.bjjacademy.enums.CategoriaFaixa;
import br.com.bjjacademy.enums.CodigoFaixa;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 40, updatable = false)
    private CodigoFaixa codigo;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaFaixa categoria;

    @Column(name = "cor_principal_hex", nullable = false, length = 7)
    private String corPrincipalHex;

    @Column(name = "cor_secundaria_hex", length = 7)
    private String corSecundariaHex;

    @Column(name = "cor_tarja_hex", nullable = false, length = 7)
    private String corTarjaHex;

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
