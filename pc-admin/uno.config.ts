import { defineConfig, presetAttributify, presetIcons, presetUno } from 'unocss';

export default defineConfig({
  presets: [presetUno(), presetAttributify(), presetIcons()],
  theme: {
    colors: {
      brand: {
        50: '#f3f7ff',
        100: '#dde9ff',
        500: '#205493',
        700: '#163a66',
        900: '#102542'
      },
      sand: {
        50: '#f8f5ee',
        100: '#efe6d8',
        300: '#d8c2a7',
        500: '#b47b45'
      }
    }
  }
});
