package org.jcvalram.api.service;

import java.util.List;

import org.jcvalram.api.entity.Album;

public interface IAlbumService {

	List<Album> buscarTodos();
	void guardar(Album album);
	void eliminar(Integer id);
}
