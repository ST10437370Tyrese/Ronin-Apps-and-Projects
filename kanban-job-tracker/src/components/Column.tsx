import { useDroppable } from '@dnd-kit/core';
import { SortableContext, verticalListSortingStrategy } from '@dnd-kit/sortable';
import { Plus } from 'lucide-react';
import type { Job, Status } from '../types';
import { JobCard } from './JobCard';

interface ColumnProps {
  id: Status;
  title: string;
  jobs: Job[];
  onDeleteJob: (id: string) => void;
  onAddClick: () => void;
}

export function Column({ id, title, jobs, onDeleteJob, onAddClick }: ColumnProps) {
  const { setNodeRef } = useDroppable({ id });

  return (
    <div className="w-full md:w-80 md:shrink-0 flex flex-col">
      <div className="flex justify-between items-center mb-4 px-2">
        <h2 className="font-semibold text-gray-600 dark:text-gray-300 text-sm tracking-wide uppercase">{title}</h2>
        <div className="flex items-center gap-2">
          <span className="text-xs font-bold text-gray-500 bg-gray-200 dark:bg-gray-900 px-2 py-0.5 rounded-md">
            {jobs.length}
          </span>
          <button onClick={onAddClick} className="text-gray-400 hover:text-black dark:hover:text-white transition-colors">
            <Plus size={18} />
          </button>
        </div>
      </div>
      
      <div 
        ref={setNodeRef} 
        className="bg-gray-100 dark:bg-[#0f0f0f] rounded-2xl p-3 flex-1 overflow-y-auto min-h-[200px] md:max-h-[70vh] border border-gray-200 dark:border-gray-900/50 space-y-3"
      >
        <SortableContext items={jobs.map((j) => j.id)} strategy={verticalListSortingStrategy}>
          {jobs.map((job) => (
            <JobCard key={job.id} job={job} onDelete={onDeleteJob} />
          ))}
        </SortableContext>
        
        {jobs.length === 0 && (
          <div className="h-full flex flex-col items-center justify-center text-gray-400 dark:text-gray-600 py-10 border-2 border-dashed border-gray-300 dark:border-gray-800 rounded-xl">
            <p className="text-sm font-medium">Empty</p>
            <p className="text-xs">Drop items here</p>
          </div>
        )}
      </div>
    </div>
  );
}