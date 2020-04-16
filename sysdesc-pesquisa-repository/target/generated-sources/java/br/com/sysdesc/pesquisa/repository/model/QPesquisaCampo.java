package br.com.sysdesc.pesquisa.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPesquisaCampo is a Querydsl query type for PesquisaCampo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPesquisaCampo extends EntityPathBase<PesquisaCampo> {

    private static final long serialVersionUID = 339752526L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPesquisaCampo pesquisaCampo = new QPesquisaCampo("pesquisaCampo");

    public final StringPath alias = createString("alias");

    public final StringPath campo = createString("campo");

    public final NumberPath<Long> codigoPesquisa = createNumber("codigoPesquisa", Long.class);

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> flagFormatacao = createNumber("flagFormatacao", Long.class);

    public final NumberPath<Long> flagOrdem = createNumber("flagOrdem", Long.class);

    public final NumberPath<Long> flagTipoDado = createNumber("flagTipoDado", Long.class);

    public final NumberPath<Long> flagTipoTamanho = createNumber("flagTipoTamanho", Long.class);

    public final StringPath formato = createString("formato");

    public final NumberPath<Long> idPesquisacampo = createNumber("idPesquisacampo", Long.class);

    public final NumberPath<Long> numeroTamanho = createNumber("numeroTamanho", Long.class);

    public final QPesquisa pesquisa;

    public QPesquisaCampo(String variable) {
        this(PesquisaCampo.class, forVariable(variable), INITS);
    }

    public QPesquisaCampo(Path<? extends PesquisaCampo> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPesquisaCampo(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPesquisaCampo(PathMetadata<?> metadata, PathInits inits) {
        this(PesquisaCampo.class, metadata, inits);
    }

    public QPesquisaCampo(Class<? extends PesquisaCampo> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pesquisa = inits.isInitialized("pesquisa") ? new QPesquisa(forProperty("pesquisa"), inits.get("pesquisa")) : null;
    }

}

