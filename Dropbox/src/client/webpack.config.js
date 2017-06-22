var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, '..\\..\\public');
var APP_DIR = path.resolve(__dirname, 'app');

var config = {
  entry: {
	  home: [APP_DIR + '\\index.jsx']},
  output: {
    path: BUILD_DIR,
    filename: 'bundle.js'
  },
  module : {
    loaders : [
      {
        test : /\.jsx?/,
        include : APP_DIR,
        loader : 'babel'
      },{
    	  test: /\.css$/,
    	  loader: 'style-loader'
    	}, {
    	  test: /\.css$/,
    	  loader: 'css-loader',
    	  query: {
    	    modules: true,
    	    localIdentName: '[name]__[local]___[hash:base64:5]'
    	  }
    	},
    	{
    		  test: /\.png$/,
    		  loader: 'file-loader',
    		  options: {
    		    name: '[hash].[ext]'
    		  }
    		}
      ]
  },
};

module.exports = config;
