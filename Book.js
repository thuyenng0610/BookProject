var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/myDatabase');

var Schema = mongoose.Schema;

var bookSchema = new Schema({
	title: {type: String, required: true, unique: true},
	author: String,
    description: {type: String, "default": "TBA"}
    });

module.exports = mongoose.model('Book', bookSchema);

bookSchema.methods.standardizeName = function() {
    this.title = this.title.toLowerCase();
    return this.title;
}
