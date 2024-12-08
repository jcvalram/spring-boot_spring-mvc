package org.jcvalram.api.controller;

import java.util.List;

import org.jcvalram.api.entity.Album;
import org.jcvalram.api.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
public class AlbumController {
	
	@Autowired
	private IAlbumService albumService;

	@GetMapping("/albums")
	public List<Album> buscarTodos() {
		return albumService.buscarTodos();
	}
	
	@PostMapping("/albums")
	public Album crearAlbum(@RequestBody Album album) {
		albumService.guardar(album);
		return album;
	}
	
	@PutMapping("/albums")
	public Album modificarAlbum(@RequestBody Album album) {
		albumService.guardar(album);
		return album;
	}
	
	@DeleteMapping("/albums/{id}")
	public String modificarAlbum(@PathVariable("id") Integer id) {
		albumService.eliminar(id);
		return "Album elminado";
	}
}