package org.jcvalram.api.service.impl;

import java.util.List;

import org.jcvalram.api.entity.Album;
import org.jcvalram.api.repository.AlbumRepository;
import org.jcvalram.api.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceIpml implements IAlbumService {

	@Autowired
	private AlbumRepository repository;
	
	public List<Album> buscarTodos() {
		return repository.findAll();
	}

	public void guardar(Album album) {
		repository.save(album);
	}

	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}
