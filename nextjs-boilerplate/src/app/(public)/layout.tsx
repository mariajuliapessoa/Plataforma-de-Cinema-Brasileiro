import NavBar from "./_components/navigation/navbar";
import Footer from "./_components/navigation/footer";

export default function PublicLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="w-full flex flex-col justify-center items-center select-none">
      <NavBar />
      <main className="pt-32 px-4 xl:max-w-5xl">
        {children}
        <Footer />
      </main>
    </div>
  );
}