/* eslint-disable */

const merge = require('webpack-merge')
const common = require('./webpack.common.js')
const path = require('path')

const mock = require('./dev/api.json')
const fs = require('fs')

const config = {
  mode: 'development',
  devtool: 'inline-source-map',
  devServer: {
    contentBase: path.resolve(__dirname, 'public'),
    historyApiFallback: true,
    compress: true,
    open: false,
    before: function(app, server, compiler) {
      console.log("....")
      app.get('/metadata/client', function(req, res) {
        console.log("....")
        setTimeout(() => {
          res.json(mock)
        }, 800)
      })
      app.get('/starter.zip', function(req, res) {
        console.log("....")
        fs.readFile(path.resolve('./dev/starter.zip'), (err, data) => {
          if (err) return sendError(err, res)
          console.log("....")
          setTimeout(() => {
            res.send(data)
          }, 800)
        })
      })
    },
  },
}

module.exports = merge(common, config)
