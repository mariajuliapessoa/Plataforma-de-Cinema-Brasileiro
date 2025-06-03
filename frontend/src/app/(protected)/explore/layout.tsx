import Navbar from "./_components/navigation/navbar";

export default function ExploreLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <Navbar />
      <div className="w-full max-w-7xl px-4 py-10">
        {children}
      </div>
    </div>
  );
}