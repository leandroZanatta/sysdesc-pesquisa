package br.com.sysdesc.pesquisa.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPesquisa is a Querydsl query type for Pesquisa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPesquisa extends EntityPathBase<Pesquisa> {

    private static final long serialVersionUID = 80016288L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPesquisa pesquisa = new QPesquisa("pesquisa");

    public final NumberPath<Long> codigoPesquisa = createNumber("codigoPesquisa", Long.class);

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> idPesquisa = createNumber("idPesquisa", Long.class);

    public final NumberPath<Long> paginacao = createNumber("paginacao", Long.class);

    public final ListPath<PermissaoPesquisa, QPermissaoPesquisa> permissaoPesquisas = this.<PermissaoPesquisa, QPermissaoPesquisa>createList("permissaoPesquisas", PermissaoPesquisa.class, QPermissaoPesquisa.class, PathInits.DIRECT2);

    public final ListPath<PesquisaCampo, QPesquisaCampo> pesquisaCampos = this.<PesquisaCampo, QPesquisaCampo>createList("pesquisaCampos", PesquisaCampo.class, QPesquisaCampo.class, PathInits.DIRECT2);

    public final QPesquisaPadrao pesquisaPadrao;

    public final NumberPath<Long> tipo = createNumber("tipo", Long.class);

    public QPesquisa(String variable) {
        this(Pesquisa.class, forVariable(variable), INITS);
    }

    public QPesquisa(Path<? extends Pesquisa> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPesquisa(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPesquisa(PathMetadata<?> metadata, PathInits inits) {
        this(Pesquisa.class, metadata, inits);
    }

    public QPesquisa(Class<? extends Pesquisa> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pesquisaPadrao = inits.isInitialized("pesquisaPadrao") ? new QPesquisaPadrao(forProperty("pesquisaPadrao"), inits.get("pesquisaPadrao")) : null;
    }

}

