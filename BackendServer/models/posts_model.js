var mongoose = require('mongoose');


const TESTING = true;

// Post schema
var schema = mongoose.Schema({

    user_id:{
        required:true,
        type:Number
    },
    title:{
        required:true,
        type:String
    },
    description:{
        required:true,
        type:String
    },
    showEmail:{
        type:Boolean,
        default:false
    },
    showPhone:{
        type:Boolean,
        default:false
    },
    images:{
        type:Array,
        default:[]
    },
    date:{
        type:Date,
        default:Date.now()
    }
});

module.exports = mongoose.model('posts', schema);

const Posts = module.exports;


module.exports.addPosting = function (post, cb) {

    Posts.create(post, cb);

};

module.exports.getAllPosts = function (cb) {

    Posts.find({}, cb);

};

module.exports.updateFields = function (id, token, updates, cb){

    Posts.updateOne(
        {_id:id},
        { $set: updates },
        cb
    );

};

/**
 * Gets a specific post
 * @param id - post's ID
 * @param cb - callback function
 */
module.exports.findById = function (id, cb) {

    Posts.findOne(
        {_id:id},
        cb
    );
};

/**
 * Clears all posts's from the database (NOT FOR PRODUCTION)
 * @param cb
 */
module.exports.clearAll = function (cb) {

    Posts.remove({}, cb)
};
