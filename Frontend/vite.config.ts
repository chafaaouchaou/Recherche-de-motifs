import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import type { UserConfig } from 'vitest/config';

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true, // Active les variables globales de Vitest (comme `describe`, `it`, etc.)
    environment: 'jsdom', // Environnement de test pour React (simule un navigateur)
  },
} as UserConfig);