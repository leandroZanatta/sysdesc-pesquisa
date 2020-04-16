package br.com.sysdesc.pesquisa.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPesquisaPadrao is a Querydsl query type for PesquisaPadrao
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPesquisaPadrao extends EntityPathBase<PesquisaPadrao> {

    private static final long serialVersionUID = -1980661139L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPesquisaPadrao pesquisaPadrao = new QPesquisaPadrao("pesquisaPadrao");

    public final NumberPath<Long> codigoPesquisa = createNumber("codigoPesquisa", Long.class);

    public final NumberPath<Long> codigoUsuario = createNumber("codigoUsuario", Long.class);

    public final br.com.sysdesc.pesquisa.repository.model.pk.QPesquisaPadraoPk id;

    public final QPesquisa pesquisa;

    public QPesquisaPadrao(String variable) {
        this(PesquisaPadrao.class, forVariable(variable), INITS);
    }

    public QPesquisaPadrao(Path<? extends PesquisaPadrao> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPesquisaPadrao(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPesquisaPadrao(PathMetadata<?> metadata, PathInits inits) {
        this(PesquisaPadrao.class, metadata, inits);
    }

    public QPesquisaPadrao(Class<? extends PesquisaPadrao> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new br.com.sysdesc.pesquisa.repository.model.pk.QPesquisaPadraoPk(forProperty("id")) : null;
        this.pesquisa = inits.isInitialized("pesquisa") ? new QPesquisa(forProperty("pesquisa"), inits.get("pesquisa")) : null;
    }

}

