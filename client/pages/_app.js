import '../styles/globals.css';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';

import Layout from '../components/layout/layout';

function MyApp({ Component, pageProps }) {
  const getLayout = Component.getLayout || ((page) => page);

  return (
    <PersistGate persistor={persistor} loading={<div>loading...</div>}>
      <Layout>{getLayout(<Component {...pageProps} />)}</Layout>
    </PersistGate>
  );
}
export default wrapper.withRedux(MyApp);
