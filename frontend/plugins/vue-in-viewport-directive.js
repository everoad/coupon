import Vue from 'vue'
import inViewportDirective from 'vue-in-viewport-directive'

inViewportDirective.defaults.margin = '-10% 0%'
Vue.directive('in-viewport', inViewportDirective)
