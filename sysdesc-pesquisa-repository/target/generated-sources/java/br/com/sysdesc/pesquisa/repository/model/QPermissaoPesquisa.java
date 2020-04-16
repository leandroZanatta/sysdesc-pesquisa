package br.com.sysdesc.pesquisa.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPermissaoPesquisa is a Querydsl query type for PermissaoPesquisa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPermissaoPesquisa extends EntityPathBase<PermissaoPesquisa> {

    private static final long serialVersionUID = 587773521L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPermissaoPesquisa permissaoPesquisa = new QPermissaoPesquisa("permissaoPesquisa");

    public final NumberPath<Long> codigoPerfil = createNumber("codigoPerfil", Long.class);

    public final NumberPath<Long> codigoPesquisa = createNumber("codigoPesquisa", Long.class);

    public final NumberPath<Long> codigoUsuario = createNumber("codigoUsuario", Long.class);

    public final NumberPath<Long> idPermissaopesquisa = createNumber("idPermissaopesquisa", Long.class);

    public final QPesquisa pesquisa;

    public QPermissaoPesquisa(String variable) {
        this(PermissaoPesquisa.class, forVariable(variable), INITS);
    }

    public QPermissaoPesquisa(Path<? extends PermissaoPesquisa> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPermissaoPesquisa(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPermissaoPesquisa(PathMetadata<?> metadata, PathInits inits) {
        this(PermissaoPesquisa.class, metadata, inits);
    }

    public QPermissaoPesquisa(Class<? extends PermissaoPesquisa> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pesquisa = inits.isInitialized("pesquisa") ? new QPesquisa(forProperty("pesquisa"), inits.get("pesquisa")) : null;
    }

}

