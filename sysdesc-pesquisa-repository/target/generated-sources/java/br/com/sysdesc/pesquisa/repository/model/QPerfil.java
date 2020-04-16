package br.com.sysdesc.pesquisa.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPerfil is a Querydsl query type for Perfil
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPerfil extends EntityPathBase<Perfil> {

    private static final long serialVersionUID = 1023505079L;

    public static final QPerfil perfil = new QPerfil("perfil");

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> idPerfil = createNumber("idPerfil", Long.class);

    public QPerfil(String variable) {
        super(Perfil.class, forVariable(variable));
    }

    public QPerfil(Path<? extends Perfil> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfil(PathMetadata<?> metadata) {
        super(Perfil.class, metadata);
    }

}

