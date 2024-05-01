/**
 * 
 * @author Sébastien Bordes
 */
open module org.jrebirth.demo.comparisontool {

	exports org.jrebirth.demo.comparisontool;
	exports org.jrebirth.demo.comparisontool.bean;
	exports org.jrebirth.demo.comparisontool.command;
	exports org.jrebirth.demo.comparisontool.resources;
	exports org.jrebirth.demo.comparisontool.service;
	exports org.jrebirth.demo.comparisontool.ui;

	provides org.jrebirth.af.api.module.ModuleStarter with org.jrebirth.demo.comparisontool.ComparisontoolModuleStarter;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;

	requires java.desktop;
	
	requires transitive org.jrebirth.af.api;
	requires org.jrebirth.af.core;
    requires org.jrebirth.af.resources;
	requires org.jrebirth.af.component;

	requires java.naming;
	requires ch.qos.logback.classic;
	requires org.apache.commons.csv;
}
