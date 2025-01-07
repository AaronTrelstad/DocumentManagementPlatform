import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import { Header } from './components/header';
import { Dashboard } from './pages/dashboard';
import { Home } from './pages/home';

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <>
        <Header />
        <Outlet />
      </>
    ),
    children: [
      {
        path: "/",
        element: <Home />
      },
      {
        path: "/dashboard",
        element: <Dashboard />
      },
    ]
  }
]);

function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
