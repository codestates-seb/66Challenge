import dynamic from 'next/dynamic';

export const ReactQuillWrapper = dynamic(() => import('react-quill'), {
  ssr: false,
});
