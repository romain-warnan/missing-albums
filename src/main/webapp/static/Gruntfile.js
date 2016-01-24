module.exports = function(grunt) {

	grunt.initConfig({

		pkg: grunt.file.readJSON('package.json'),

		uglify: {
			build: {
				src: 'js/*.js',
				dest: 'build/application.min.js'
			}
		},

		compass: {
			dist: {
				options: {
					config: 'config.rb'
				}
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-compass');

	grunt.registerTask('default', ['uglify', 'compass']);
};