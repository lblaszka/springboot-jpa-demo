package com.github.lblaszka.springbootjpademo.controller;

import com.github.lblaszka.springbootjpademo.domain.Library;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("library")
public class LibraryController extends TemplateController<Library, Long>
{
}
