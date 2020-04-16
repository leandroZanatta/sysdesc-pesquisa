package br.com.sysdesc.pesquisa.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUsuario is a Querydsl query type for Usuario
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUsuario extends EntityPathBase<Usuario> {

    private static final long serialVersionUID = -2085091421L;

    public static final QUsuario usuario = new QUsuario("usuario");

    public final NumberPath<Long> codigoCliente = createNumber("codigoCliente", Long.class);

    public final NumberPath<Long> idUsuario = createNumber("idUsuario", Long.class);

    public final StringPath nomeUsuario = createString("nomeUsuario");

    public final ListPath<PerfilUsuario, QPerfilUsuario> perfilUsuarios = this.<PerfilUsuario, QPerfilUsuario>createList("perfilUsuarios", PerfilUsuario.class, QPerfilUsuario.class, PathInits.DIRECT2);

    public final StringPath senha = createString("senha");

    public QUsuario(String variable) {
        super(Usuario.class, forVariable(variable));
    }

    public QUsuario(Path<? extends Usuario> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsuario(PathMetadata<?> metadata) {
        super(Usuario.class, metadata);
    }

}

