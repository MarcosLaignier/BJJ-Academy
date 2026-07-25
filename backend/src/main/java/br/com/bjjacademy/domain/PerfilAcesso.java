package br.com.bjjacademy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "perfil_acesso")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfilAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    @Column(length = 300)
    private String descricao;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @ManyToMany
    @JoinTable(name = "perfil_permissao",
            joinColumns = @JoinColumn(name = "perfil_acesso_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    @Builder.Default
    private Set<Permissao> permissoes = new LinkedHashSet<>();
}
